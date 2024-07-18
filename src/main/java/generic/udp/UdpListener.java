package generic.udp;

import generic.data.DataHandler;
import generic.data.parsing.DataParser;
import generic.data.parsing.ParsedData;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Listens for UDP packets and forwards them to registered handlers after parsing.
 */
public class UdpListener<D extends ParsedData> {

    private final DatagramSocket socket;
    private final AtomicBoolean run = new AtomicBoolean(false);

    private final List<DataHandler<D>> handlers = new ArrayList<>();
    private final List<DataParser<D>> parsers;

    public UdpListener(InetAddress address, int port, List<DataParser<D>> parsers) {
        this.parsers = parsers;
        try {
            SocketAddress socketAddress = new InetSocketAddress(address, port);
            socket = new DatagramSocket(socketAddress);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void addHandler(DataHandler<D> handler) {
        handlers.add(handler);
    }

    public synchronized void removeHandler(DataHandler<D> handler) {
        handlers.remove(handler);
    }

    public void run() throws IOException {
        run.set(true);
        while (run.get()) {
            byte[] buffer = new byte[65535];
            DatagramPacket packet = new DatagramPacket(buffer, 65535);
            socket.receive(packet);
            forwardReceivedPacket(packet);
        }
    }

    public void stop() {
        run.set(false);
    }

    private synchronized void forwardReceivedPacket(DatagramPacket packet) {
        D data = null;
        for (DataParser<D> parser : parsers) {
            data = parser.parse(Arrays.copyOfRange(packet.getData(), 0, packet.getLength()));
            if (data != null) {
                D finalData = data;
                handlers.forEach(h -> {
                    if (h.getDataType().isAssignableFrom(finalData.getClass())) {
                            h.handleData(finalData);
                    }
                });
            }
            break;
        }
    }
}
