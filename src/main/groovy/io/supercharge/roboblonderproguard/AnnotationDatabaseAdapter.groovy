package io.supercharge.roboblonderproguard

import com.github.javaparser.ast.expr.MethodCallExpr
import com.github.javaparser.ast.expr.NameExpr
import com.github.javaparser.ast.expr.StringLiteralExpr
import com.github.javaparser.ast.visitor.GenericVisitorAdapter

public class AnnotationDatabaseAdapter extends GenericVisitorAdapter<Void, Void> {

    private List<String> referencedClassNames = new ArrayList<>()

    @Override
    Void visit(MethodCallExpr n, Void arg) {
        if (n.name == "put" && n.args.size() == 2) {
            def firstArg = n.args.first()

            if (firstArg instanceof StringLiteralExpr) {
                referencedClassNames += firstArg.value
            }
        }

        if (n.name == "add" && n.args.size() == 1) {
            def firstArg = n.args.first()

            if (firstArg instanceof StringLiteralExpr && n.scope instanceof NameExpr) {
                NameExpr scope = n.scope as NameExpr

                if (scope.name == "methodSet" || scope.name == "constructorSet" || scope.name == "classesContainingInjectionPointsSet" || scope.name == "injectedClasses") {
                    if (firstArg.value.startsWith("<init>:") || firstArg.value.startsWith("init:")) {
                        firstArg.value.replace("<init>:", "").replace("init:", "").split(":").each { className ->
                            referencedClassNames += className
                        }
                    } else if (firstArg.value != "<init>"){
                        referencedClassNames += firstArg.value
                    }
                }
            }
        }

        return super.visit(n, arg);
    }
}