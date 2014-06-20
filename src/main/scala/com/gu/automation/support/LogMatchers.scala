package com.gu.automation.support

import org.scalatest.Matchers
import org.scalatest.enablers._
import org.scalatest.matchers._
import org.scalatest.words._
import org.scalautils.TripleEqualsSupport.{Spread, TripleEqualsInvocation, TripleEqualsInvocationOnSpread}
import org.scalautils.{Constraint, Equality}

/**
 * Created by jduffell on 19/06/2014.
 */

trait LogMatchers extends Matchers {

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * <p>
   * This class is used in conjunction with an implicit conversion to enable <code>should</code> methods to
   * be invoked on objects of type <code>Any</code>.
   * </p>
   *
   * @author Bill Venners
   */
  class LoggingAnyShouldWrapper[T](left: T) extends AnyShouldWrapper[T](left) {

    def log[R,T](right: R)(f: R => T) = {
      println(s"LOG: $left should $right")
      f(right)
    }

    def lshould(rightMatcherX1: Matcher[T]): Unit = log(rightMatcherX1)(super.should(_))

    def lshould[TYPECLASS1[_]](rightMatcherFactory1: MatcherFactory1[T, TYPECLASS1])(implicit typeClass1: TYPECLASS1[T]): Unit = log(rightMatcherFactory1)(super.should(_))

    def lshould[TYPECLASS1[_], TYPECLASS2[_]](rightMatcherFactory2: MatcherFactory2[T, TYPECLASS1, TYPECLASS2])(implicit typeClass1: TYPECLASS1[T], typeClass2: TYPECLASS2[T]): Unit = log(rightMatcherFactory2)(super.should(_))

    def lshouldEqual(right: Any)(implicit equality: Equality[T]): Unit = log(right)(super.shouldEqual(_))

    def lshouldEqual(spread: Spread[T]): Unit = log(spread)(super.shouldEqual(_))

    def lshouldEqual(right: Null)(implicit ev: <:<[T, AnyRef]): Unit = log(right)(super.shouldEqual(_))

    def lshould(notWord: NotWord): ResultOfNotWordForAny[T] = log(notWord)(super.should(_))

    def lshould[U](inv: TripleEqualsInvocation[U])(implicit constraint: Constraint[T, U]): Unit = log(inv)(super.should(_))

    def lshould(inv: TripleEqualsInvocationOnSpread[T])(implicit ev: Numeric[T]): Unit = log(inv)(super.should(_))

    def lshould(beWord: BeWord): ResultOfBeWordForAny[T] = log(beWord)(super.should(_))

    def lshouldBe(right: Any): Unit = log(right)(super.shouldBe(_))

    def lshouldBe(comparison: ResultOfLessThanComparison[T]): Unit = log(comparison)(super.shouldBe(_))

    def lshouldBe(comparison: ResultOfGreaterThanComparison[T]): Unit = log(comparison)(super.shouldBe(_))

    def lshouldBe(comparison: ResultOfLessThanOrEqualToComparison[T]): Unit = log(comparison)(super.shouldBe(_))

    def lshouldBe(comparison: ResultOfGreaterThanOrEqualToComparison[T]): Unit = log(comparison)(super.shouldBe(_))

    def lshouldBe(beMatcher: BeMatcher[T]): Unit = log(beMatcher)(super.shouldBe(_))

    def lshouldBe(spread: Spread[T]): Unit = log(spread)(super.shouldBe(_))

    def lshouldBe(right: SortedWord)(implicit sortable: Sortable[T]): Unit = log(right)(super.shouldBe(_))

    def lshouldBe(aType: ResultOfATypeInvocation[_]): Unit = log(aType)(super.shouldBe(_))

    def lshouldBe(anType: ResultOfAnTypeInvocation[_]): Unit = log(anType)(super.shouldBe(_))

    def lshouldBe(right: ReadableWord)(implicit readability: Readability[T]): Unit = log(right)(super.shouldBe(_))

    def lshouldBe(right: WritableWord)(implicit writability: Writability[T]): Unit = log(right)(super.shouldBe(_))

    def lshouldBe(right: EmptyWord)(implicit emptiness: Emptiness[T]): Unit = log(right)(super.shouldBe(_))

    def lshouldBe(right: DefinedWord)(implicit definition: Definition[T]): Unit = log(right)(super.shouldBe(_))

    def lshouldNot(beWord: BeWord): ResultOfBeWordForAny[T] = log(beWord)(super.shouldNot(_))

    def lshouldNot(rightMatcherX1: Matcher[T]): Unit = log(rightMatcherX1)(super.shouldNot(_))

    def lshouldNot[TYPECLASS1[_]](rightMatcherFactory1: MatcherFactory1[T, TYPECLASS1])(implicit typeClass1: TYPECLASS1[T]): Unit = log(rightMatcherFactory1)(super.shouldNot(_))

    def lshouldNot(haveWord: HaveWord): ResultOfHaveWordForExtent[T] = log(haveWord)(super.shouldNot(_))

    def lshould(haveWord: HaveWord): ResultOfHaveWordForExtent[T] = log(haveWord)(super.should(_))

    def lshouldBe(right: Null)(implicit ev: <:<[T, AnyRef]): Unit = log(right)(super.shouldBe(_))

    def lshouldBe(resultOfSameInstanceAsApplication: ResultOfTheSameInstanceAsApplication)(implicit toAnyRef: <:<[T, AnyRef]): Unit = log(resultOfSameInstanceAsApplication)(super.shouldBe(_))

    def lshouldBe(symbol: Symbol)(implicit toAnyRef: <:<[T, AnyRef]): Unit = log(symbol)(super.shouldBe(_))

    def lshouldBe(resultOfAWordApplication: ResultOfAWordToSymbolApplication)(implicit toAnyRef: <:<[T, AnyRef]): Unit = log(resultOfAWordApplication)(super.shouldBe(_))

    def lshouldBe(resultOfAnWordApplication: ResultOfAnWordToSymbolApplication)(implicit toAnyRef: <:<[T, AnyRef]): Unit = log(resultOfAnWordApplication)(super.shouldBe(_))

    def lshouldBe(bePropertyMatcher: BePropertyMatcher[T])(implicit ev: <:<[T, AnyRef]): Unit = log(bePropertyMatcher)(super.shouldBe(_))

    def lshouldBe[U >: T](resultOfAWordApplication: ResultOfAWordToBePropertyMatcherApplication[U])(implicit ev: <:<[T, AnyRef]): Unit = log(resultOfAWordApplication)(super.shouldBe(_))

    def lshouldBe[U >: T](resultOfAnWordApplication: ResultOfAnWordToBePropertyMatcherApplication[U])(implicit ev: <:<[T, AnyRef]): Unit = log(resultOfAnWordApplication)(super.shouldBe(_))

    def lshould(containWord: ContainWord): ResultOfContainWord[T] = log(containWord)(super.should(_))

    def lshouldNot(contain: ContainWord): ResultOfContainWord[T] = log(contain)(super.shouldNot(_))

    def lshould(existWord: ExistWord)(implicit existence: Existence[T]): Unit = log(existWord)(super.should(_))

    def lshould(notExist: ResultOfNotExist)(implicit existence: Existence[T]): Unit = log(notExist)(super.should(_))

    def lshouldNot(existWord: ExistWord)(implicit existence: Existence[T]): Unit = log(existWord)(super.shouldNot(_))
  }

  /**
   * This class is part of the ScalaTest matchers DSL. Please see the documentation for <a href="Matchers.html"><code>Matchers</code></a> for an overview of
   * the matchers DSL.
   *
   * <p>
   * This class is used in conjunction with an implicit conversion to enable <code>should</code> methods to
   * be invoked on <code>String</code>s.
   * </p>
   *
   * @author Bill Venners
   */
  final class LoggingStringShouldWrapper(val leftSideValue: String) extends LoggingAnyShouldWrapper(leftSideValue) with StringShouldWrapperForVerb {

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string should include regex ("hi")
     *        ^
     * </pre>
     */
    def lshould(includeWord: IncludeWord): ResultOfIncludeWordForString = {
      new ResultOfIncludeWordForString(leftSideValue, true)
    }

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string should startWith regex ("hello")
     *        ^
     * </pre>
     */
    def lshould(startWithWord: StartWithWord): ResultOfStartWithWordForString = {
      new ResultOfStartWithWordForString(leftSideValue, true)
    }

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string should endWith regex ("world")
     *        ^
     * </pre>
     */
    def lshould(endWithWord: EndWithWord): ResultOfEndWithWordForString = {
      new ResultOfEndWithWordForString(leftSideValue, true)
    }

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string should fullyMatch regex ("""(-)?(\d+)(\.\d*)?""")
     *        ^
     * </pre>
     */
    def lshould(fullyMatchWord: FullyMatchWord): ResultOfFullyMatchWordForString = {
      new ResultOfFullyMatchWordForString(leftSideValue, true)
    }

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string should not have length (3)
     *        ^
     * </pre>
     */
    override def lshould(notWord: NotWord): ResultOfNotWordForString = {
      new ResultOfNotWordForString(leftSideValue, false)
    }

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string should fullyMatch regex ("a(b*)c" withGroup "bb")
     *                                          ^
     * </pre>
     */
    def lwithGroup(group: String) =
      new RegexWithGroups(leftSideValue.r, IndexedSeq(group))

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string should fullyMatch regex ("a(b*)(c*)" withGroups ("bb", "cc"))
     *                                             ^
     * </pre>
     */
    def lwithGroups(groups: String*) =
      new RegexWithGroups(leftSideValue.r, IndexedSeq(groups: _*))

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string shouldNot fullyMatch regex ("""(-)?(\d+)(\.\d*)?""")
     *        ^
     * </pre>
     */
    def lshouldNot(fullyMatchWord: FullyMatchWord): ResultOfFullyMatchWordForString =
      new ResultOfFullyMatchWordForString(leftSideValue, false)

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string shouldNot startWith regex ("hello")
     *        ^
     * </pre>
     */
    def lshouldNot(startWithWord: StartWithWord): ResultOfStartWithWordForString =
      new ResultOfStartWithWordForString(leftSideValue, false)

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string shouldNot endWith regex ("world")
     *        ^
     * </pre>
     */
    def lshouldNot(endWithWord: EndWithWord): ResultOfEndWithWordForString =
      new ResultOfEndWithWordForString(leftSideValue, false)

    /**
     * This method enables syntax such as the following:
     *
     * <pre class="stHighlight">
     * string shouldNot include regex ("hi")
     *        ^
     * </pre>
     */
    def lshouldNot(includeWord: IncludeWord): ResultOfIncludeWordForString =
      new ResultOfIncludeWordForString(leftSideValue, false)
  }

  /**
   * Implicitly converts an object of type <code>T</code> to a <code>AnyShouldWrapper[T]</code>,
   * to enable <code>should</code> methods to be invokable on that object.
//   */
  override implicit def convertToAnyShouldWrapper[T](o: T): AnyShouldWrapper[T] = new LoggingAnyShouldWrapper(o)

  /**
   * Implicitly converts an object of type <code>java.lang.String</code> to a <code>StringShouldWrapper</code>,
   * to enable <code>should</code> methods to be invokable on that object.
   */
  implicit def lconvertToStringShouldWrapper(o: String): LoggingStringShouldWrapper = new LoggingStringShouldWrapper(o)

}

object LogMatchers extends LogMatchers
