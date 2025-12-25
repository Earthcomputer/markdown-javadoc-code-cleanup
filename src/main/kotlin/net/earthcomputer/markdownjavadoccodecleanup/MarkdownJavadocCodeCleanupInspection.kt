package net.earthcomputer.markdownjavadoccodecleanup

import com.intellij.codeInspection.CleanupLocalInspectionTool
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.openapi.project.DumbAware
import com.siyeh.InspectionGadgetsBundle
import com.siyeh.ig.BaseInspection
import com.siyeh.ig.BaseInspectionVisitor
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

private val fixCreator = findConstructor(LocalQuickFix::class.java, $$"com.siyeh.ig.migration.MarkdownDocumentationCommentsMigrationInspection$MarkdownDocumentationCommentsMigrationFix")
private val visitorCreator = findConstructor(BaseInspectionVisitor::class.java, $$"com.siyeh.ig.migration.MarkdownDocumentationCommentsMigrationInspection$MarkdownDocumentationCommentsMigrationVisitor")

private fun findConstructor(baseClass: Class<*>, className: String): MethodHandle {
    val clazz = Class.forName(className)
    val ctor = clazz.getDeclaredConstructor()
    return MethodHandles.privateLookupIn(
        clazz,
        MethodHandles.lookup()
    ).unreflectConstructor(ctor)
        .asType(MethodType.methodType(baseClass))
}

class MarkdownJavadocCodeCleanupInspection : BaseInspection(), DumbAware, CleanupLocalInspectionTool {
    override fun buildErrorString(vararg infos: Any?): String {
        return InspectionGadgetsBundle.message("markdown.documentation.comments.migration.display.name")
    }

    override fun buildFix(vararg infos: Any?): LocalQuickFix {
        return fixCreator.invokeExact() as LocalQuickFix
    }

    override fun buildVisitor(): BaseInspectionVisitor {
        return visitorCreator.invokeExact() as BaseInspectionVisitor
    }
}
