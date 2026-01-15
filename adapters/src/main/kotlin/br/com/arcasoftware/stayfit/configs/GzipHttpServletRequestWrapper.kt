package br.com.arcasoftware.stayfit.configs

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.io.IOException
import java.util.zip.GZIPInputStream


class GzipHttpServletRequestWrapper(
    request: HttpServletRequest
) : HttpServletRequestWrapper(request) {

    private val gzipInputStream = GZIPInputStream(request.inputStream)

    private val servletInputStream = object : ServletInputStream() {

        @Throws(IOException::class)
        override fun read(): Int =
            gzipInputStream.read()

        override fun isFinished(): Boolean = false

        override fun isReady(): Boolean = true

        override fun setReadListener(readListener: ReadListener?) {
            // Not required for synchronous processing
        }
    }

    override fun getInputStream(): ServletInputStream =
        servletInputStream
}
