package t.jdk.memory.mat.permgen;

import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

public class OOMPermTest {
    public static void main(String[] args) {
        OOMPermTest o = new OOMPermTest();
        o.oom();
    }

    private void oom() {
        try {
            ClassWriter cw = new ClassWriter(0);
            cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT,
            "org/rosenjiang/test/MyAbsClass", null, "java/lang/Object",
            new String[] {});
            cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "LESS", "I",
            null, new Integer(-1)).visitEnd();
            cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "EQUAL", "I",
            null, new Integer(0)).visitEnd();
            cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "GREATER", "I",
            null, new Integer(1)).visitEnd();
            cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "absTo",
            "(Ljava/lang/Object;)I", null, null).visitEnd();
            cw.visitEnd();
            byte[] b = cw.toByteArray();

            List<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
            while (true) {
                MyClassLoader classLoader = new MyClassLoader();
                classLoader.defineClass("org.rosenjiang.test.MyAbsClass", b);
                classLoaders.add(classLoader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}