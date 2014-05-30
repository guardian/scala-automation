package com.gu.support

import org.scalatest.{fixture, Outcome, Retries}

trait TestRetries extends fixture.FeatureSpec with Retries {

  override def withFixture(test: NoArgTest): Outcome = withRetry { super.withFixture(test) }
}
