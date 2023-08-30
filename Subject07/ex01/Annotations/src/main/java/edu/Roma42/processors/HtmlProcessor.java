package edu.Roma42.processors;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import edu.Roma42.annotations.HtmlForm;
import edu.Roma42.annotations.HtmlInput;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({ "edu.Roma42.annotations.HtmlForm", "edu.Roma42.annotations.HtmlInput" })
public class HtmlProcessor extends AbstractProcessor {

    private FileObject fileObject;
    private Path filePath;
    private boolean control = true;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            if (annotation.getQualifiedName().toString().equals("edu.Roma42.annotations.HtmlForm")) {
                for (Element element : annotatedElements) {
                    if (element.getKind() == ElementKind.CLASS) {
                        TypeElement classElement = (TypeElement) element;
                        HtmlForm htmlFormAnnotation = classElement.getAnnotation(HtmlForm.class);
                        String fileName = htmlFormAnnotation.fileName();
                        String action = htmlFormAnnotation.action();
                        String method = htmlFormAnnotation.method();
                        generateHtmlForm(fileName, action, method);
                    }
                }
            }
        }
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            if (annotation.getQualifiedName().toString().equals("edu.Roma42.annotations.HtmlInput")) {
                for (Element element : annotatedElements) {
                    if (element.getKind() == ElementKind.FIELD) {
                        VariableElement variableElement = (VariableElement) element;
                        HtmlInput htmlInputAnnotation = variableElement.getAnnotation(HtmlInput.class);
                        String type = htmlInputAnnotation.type();
                        String name = htmlInputAnnotation.name();
                        String placeholder = htmlInputAnnotation.placeholder();
                        generateHtmlInput(type, name, placeholder);
                    }
                }
            }
        }
        if (control) {
            try {
                String inputCode = "<input type = \"submit\" value = \"Send\">\n</form>\n";
                Files.write(this.filePath, inputCode.getBytes(), StandardOpenOption.APPEND);
                control = false;
            } catch (IOException e) {
                System.out.println("main");
                // e.printStackTrace();
            }
        }
        return true;
    }

    private void generateHtmlForm(String fileName, String action, String method) {
        try {
            this.fileObject = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", fileName);
            this.filePath = Paths.get(this.fileObject.toUri());

            try (Writer writer = fileObject.openWriter()) {
                writer.write("<form action=\"" + action + "\" method=\"" + method + "\">\n");
            }
        } catch (IOException e) {
            System.out.println("Form");
            // e.printStackTrace();
        }
    }

    private void generateHtmlInput(String type, String name, String placeholder) {
        try {
            String inputCode = "<input type = \"" + type + "\" name = \"" + name + "\" placeholder = \"" + placeholder + "\">\n";
            Files.write(this.filePath, inputCode.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Input");
            // e.printStackTrace();
        }
    }
}
