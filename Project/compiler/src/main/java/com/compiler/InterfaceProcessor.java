package com.compiler;

import annotion.DoctorInterface;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

@AutoService(Processor.class)
public class InterfaceProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(DoctorInterface.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //创建动态代码，实际上就是创建一个String, 写入到文件里
        //然后文件会被解释为.class文件

        StringBuilder builder = new StringBuilder()
                .append("package com.user.demo;\n\n")
                .append("public class GeneratedClass {\n\n")
                .append("\tpublic String getMessage() {\n")
                .append("\t\treturn \"");

        //获取所有被CustomAnnotation修饰的代码元素
        for (Element element : roundEnvironment.getElementsAnnotatedWith(DoctorInterface.class)) {
            String objectType = element.getSimpleName().toString();
            builder.append(objectType).append(" exists!\\n");
        }

        builder.append("\";\n")
                .append("\t}\n")
                .append("}\n");

        //将String写入并生成.class文件
        try {
            JavaFileObject source = processingEnv.getFiler().createSourceFile(
                    "com.user.demo.GeneratedClass");

            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            //
        }

        //测试另外一个效果，在D盘生成对应的文件夹
        File file = new File("D://test//123");
        if (!file.exists()) {
            file.mkdirs();
        }

        return true;
    }


}