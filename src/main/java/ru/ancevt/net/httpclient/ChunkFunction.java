package ru.ancevt.net.httpclient;

/**
 *
 * @author ancevt
 */
@FunctionalInterface
public interface ChunkFunction {

    void chunk(int chunkNum, byte[] bytes);

}
