package dk.znz.drupdate;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProgressInputStream extends FilterInputStream {
    private volatile long totalNumBytesRead;
    private final ProgressInfo listener;

    public ProgressInputStream(InputStream in, ProgressInfo listener) {
        super(in);
        this.listener = listener;
    }

    public long getTotalNumBytesRead() {
        return totalNumBytesRead;
    }

    @Override
    public int read() throws IOException {
        return (int)updateProgress(super.read());
    }

    @Override
    public int read(byte[] b) throws IOException {
        return (int)updateProgress(super.read(b));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return (int)updateProgress(super.read(b, off, len));
    }

    @Override
    public long skip(long n) throws IOException {
        return updateProgress(super.skip(n));
    }

    @Override
    public void mark(int readlimit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    private long updateProgress(long numBytesRead) {
        if (numBytesRead > 0) {
            totalNumBytesRead += numBytesRead;
            listener.progressChanged(totalNumBytesRead);
        }

        return numBytesRead;
    }
}