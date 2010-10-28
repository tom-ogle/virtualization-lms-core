package scala.virtualization.lms
package common.embedded.scala

import java.io.PrintWriter
import scala.virtualization.lms.common.{Base, BaseExp}
import scala.virtualization.lms.internal.ScalaGenBase

trait BooleanOps extends Base {
  def infix_unary_!(x: Rep[Boolean]) = boolean_negate(x)

  def boolean_negate(lhs: Rep[Boolean]): Rep[Boolean]
}

trait BooleanOpsExp extends BooleanOps with BaseExp {
  case class BooleanNegate(lhs: Exp[Boolean]) extends Def[Boolean]
  
  def boolean_negate(lhs: Exp[Boolean]) : Rep[Boolean] = BooleanNegate(lhs)
}

trait ScalaGenBooleanOps extends ScalaGenBase {
  val IR: BooleanOpsExp
  import IR._

  override def emitNode(sym: Sym[_], rhs: Def[_])(implicit stream: PrintWriter) = rhs match {
    case BooleanNegate(b) => emitValDef(sym, "!" + quote(b))
    case _ => super.emitNode(sym,rhs)
  }
}
