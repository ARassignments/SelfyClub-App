package com.selfyclubb;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class CountingRequestBody extends RequestBody {
    private final RequestBody delegate;
    private final UploadProgressListener listener;

    public interface UploadProgressListener {
        void onProgress(long bytesWritten, long contentLength);
    }

    public CountingRequestBody(RequestBody delegate, UploadProgressListener listener) {
        this.delegate = delegate;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return delegate.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        delegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private final class CountingSink extends ForwardingSink {
        private long bytesWritten = 0;
        private long lastUpdateProgress = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            long totalBytes = contentLength();

            if (totalBytes > 0) {
                int newProgress = (int) ((100 * bytesWritten) / totalBytes);

                // Only update if progress increased by at least 1%
                if (newProgress > lastUpdateProgress) {
                    lastUpdateProgress = newProgress;
                    listener.onProgress(bytesWritten, totalBytes);
                }
            }
        }
    }
}