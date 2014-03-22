package com.aif.r

import r.data.{RAny, RSymbol}
import r.nodes.ast.ASTNode
import r.RContext
import r.parser.{RParser, RLexer}
import org.antlr.runtime.{CommonTokenStream, ANTLRStringStream}

object RWrapper {

  def evalR(input: String):String = {
    var result = eval(input).pretty();
    result = result.replace("\r\n", "\n");
    return result;
  }

  private def parse(input: String): ASTNode = {
    parser.reset
    lexer.resetIncomplete
    lexer.setCharStream(new ANTLRStringStream(input))
    parser.setTokenStream(new CommonTokenStream(lexer))
    return parser.interactive
  }

  private def eval(input: String): RAny = {
    val astNode: ASTNode = parse(input)
    try {
      return RContext.eval(astNode, true)
    }
    finally {
      RSymbol.resetTable
    }
  }

  private var lexer: RLexer = new RLexer

  private var parser: RParser = new RParser(null)

}
