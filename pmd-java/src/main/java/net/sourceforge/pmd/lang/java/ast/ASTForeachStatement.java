/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.ast;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a "foreach"-loop on an {@link Iterable}.
 *
 * <pre class="grammar">
 *
 * ForeachStatement ::= "for" "(" {@linkplain ASTLocalVariableDeclaration LocalVariableDeclaration} ":" {@linkplain ASTExpression Expression} ")" {@linkplain ASTStatement Statement}
 *
 * </pre>
 */
public final class ASTForeachStatement extends AbstractStatement implements InternalInterfaces.VariableIdOwner {

    ASTForeachStatement(int id) {
        super(id);
    }


    @Override
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }


    @Override
    public <T> void jjtAccept(SideEffectingVisitor<T> visitor, T data) {
        visitor.visit(this, data);
    }


    @Override
    @NonNull
    public ASTVariableDeclaratorId getVarId() {
        return getFirstChildOfType(ASTLocalVariableDeclaration.class).iterator().next();
    }

    /**
     * Returns the expression that evaluates to the {@link Iterable}
     * being looped upon.
     */
    @NonNull
    public ASTExpression getIterableExpr() {
        return getFirstChildOfType(ASTExpression.class);
    }

    /**
     * Returns the statement that represents the body of this
     * loop.
     */
    public ASTStatement getBody() {
        return (ASTStatement) getChild(getNumChildren() - 1);
    }

}