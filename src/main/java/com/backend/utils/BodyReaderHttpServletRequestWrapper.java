package com.backend.utils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author FPH
 * @since 2023年7月4日15:27:19
 * 装饰器模式是一种结构型设计模式，它允许在不改变现有对象结构的情况下，动态地向对象添加额外的功能。它通过创建一个包装（装饰）对象来包裹原始对象，
 * 并在包装对象中添加新的行为，从而实现功能的增加和修改。
 * RequestWrapper 类就扮演了装饰器的角色。它继承了 HttpServletRequestWrapper，并在构造函数中接收原始的 HttpServletRequest 对象，
 * 然后在 getInputStream() 和 getReader() 方法中，它重写了这些方法，以返回一个经过包装的输入流和阅读器，从而实现对请求体的缓存和复用。
 * 当过滤器链中的第一个过滤器使用 RequestWrapper 来包装原始的 HttpServletRequest 对象时，它就能够在后续的过滤器或处理程序中传递这个包装后的请求对象，
 * 从而在不修改现有过滤器或处理程序代码的情况下，实现对请求体的重复读取操作。
 * 这样做避免了原始的 HttpServletRequest 对象中 getInputStream() 方法只能获取一次输入流的限制。
 * 装饰器模式的优势在于它能够灵活地组合对象以增强功能，同时避免了使用继承可能带来的类爆炸问题。这种模式在 Java 的标准库中广泛应用，
 * 例如 I/O 类的设计就使用了装饰器模式，使得你可以通过组合不同的流装饰器来构建出不同功能的流处理链。
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] requestBody;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = readRequestBody(request);
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedServletInputStream(requestBody);
    }

    @Override
    public BufferedReader getReader() {
        InputStreamReader isr = new InputStreamReader(getInputStream());
        return new BufferedReader(isr);
    }

    private byte[] readRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }

        return outputStream.toByteArray();
    }

    private static class CachedServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream inputStream;

        public CachedServletInputStream(byte[] buffer) {
            inputStream = new ByteArrayInputStream(buffer);
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override
        public int read() {
            return inputStream.read();
        }
    }
}
