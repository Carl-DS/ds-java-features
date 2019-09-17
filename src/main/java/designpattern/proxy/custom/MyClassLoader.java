package designpattern.proxy.custom;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author duosheng
 * @since 2019/9/16
 */
public class MyClassLoader extends ClassLoader {

    // 生成的代理类加载路径
    private File dir;

    private String proxyClassPackage;

    public String getProxyClassPackage() {
        return proxyClassPackage;
    }

    public File getDir() {
        return dir;
    }

    public MyClassLoader(String path, String proxyClassPackage) {
        this.dir = new File(path);
        this.proxyClassPackage = proxyClassPackage;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (dir != null) {
            File classFile = new File(dir, name + ".class");
            if (classFile.exists()) {
                try {
                    // 生成 CLASS 的二进制字节流
                    byte[] classBytes = FileCopyUtils.copyToByteArray(classFile);
                    return defineClass(proxyClassPackage + "." + name, classBytes, 0, classBytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 如果上述的自定义的类没有加载到,走默认的加载方式
        return super.findClass(name);
    }
}
