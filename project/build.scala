import sbt._

import scala.util.parsing.combinator.RegexParsers

object ChangeLogBuild extends Build {

  val changeLog = taskKey[Unit]("changelog generator")

  override lazy val settings = super.settings ++
    Seq(
      changeLog := doChangeLog
    )

  case class Record(tags: Option[String], date: String, user: String, subject: Either[Int,String], body: String)

  object LogParser extends RegexParsers {

    def field: Parser[String] = """[^~]*""".r

    def merge: Parser[Left[Int, Nothing]] = """Merge pull request #""".r ~> "[0-9]+".r <~ field ^^ {
      prString => Left(prString.toInt)
    }

    def subject: Parser[Right[Nothing, String]] = field ^^ { subject => Right(subject) }

    def separator: Parser[String] = "~"

    def versionTag: Parser[Option[String]] = "tag: ".r.? ~ "[^),]+".r ^^ {
      case Some(_) ~ tag if (tag.startsWith("v")) => Some(tag)
      case _ => None
    }

    def version: Parser[Option[String]] = ("(" ~> rep1sep(versionTag, ", ") <~ ")") ^^ {
      hasTags: List[Option[String]] =>
        val tagsList = hasTags.flatten
        if (tagsList.isEmpty) None
        else Some(tagsList.mkString(", "))
    }

    def versionOption: Parser[Option[String]] = version.? ^^ {
      _.flatten
    }

    def line = (versionOption <~ separator) ~ (field <~ separator) ~ (field <~ separator) ~ ((merge | subject) <~ separator) ~ (field <~ "~!~") ^^ {
      case tags ~ date  ~ user  ~ subject  ~ body  =>
        Record(tags, date, user, subject, body)
    }

    def file = line.*

    def parse(log: String) = parseAll(file, log) match {
      case Success(lines, _) => Right(lines)
      case failure: NoSuccess => Left(failure)
    }

  }

  def doChangeLog = {
    val log = {"""git log --tags --format=%d~%cD~%aN~%s~%b~!~""" !!}

    val records = LogParser.parse(log)
    val htmlLines = records.right.map(_ map {
      case Record(tags, date, user, subject, body) =>
        val subjectDesc =
          if (subject.isLeft) s"""<div><a href="https://github.com/guardian/scala-automation/pull/${subject.left.get}">Pull request #${subject.left.get}</a></div>"""
          else s"""<span>${subject.right.get}</span>"""
        if (tags.isDefined) s"""<div class="version"><h2>Release: ${tags.get}</h2><div class="credit">$date - $user</div>$subjectDesc<div>$body</div></div>""" + "\n"
        else if (subject.isLeft) s"""<div class="pullReq"><div class="credit">$date - $user</div>$subjectDesc<div>$body</div></div>""" + "\n"
        else ""
    })
    val prepend = List("<link rel='stylesheet' id='page_css' href='changelog.css' type='text/css' media='all' />")
    val file = new java.io.File("docs/local.changelog.html")
    val finalResult = htmlLines.right.map(htmlLines => IO.writeLines(file, prepend ++ htmlLines))

    if (finalResult.isLeft) {
      throw new RuntimeException("changelog parsing failed: " + finalResult.left.get)
    }
  }
}