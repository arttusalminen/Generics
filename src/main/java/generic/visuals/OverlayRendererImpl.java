package generic.visuals;

import java.awt.*;
import java.util.*;
import java.util.List;

public class OverlayRendererImpl implements OverlayRenderer {

    private final Object lock = new Object();
    private final Set<Overlay> overlays = new HashSet<>();
    private final List<Overlay> overlaysToInit = new ArrayList<>();

    public OverlayRendererImpl() {
        int targetHz = Arrays.stream(GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
                .map(d -> d.getDisplayMode().getRefreshRate())
                .reduce(Integer::max)
                .orElse(60);

        Timer t = new Timer("OverlayRenderer");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        };
        float delay = 1f / targetHz * 1000f - 2;
        delay = delay > 0 ? delay : delay + 2;
        t.schedule(task, (long) delay, (long) delay);
    }

    @Override
    public void bind(Overlay overlay) {
        synchronized (lock) {
            overlays.add(overlay);
            overlaysToInit.add(overlay);
        }
    }

    @Override
    public void unbind(Overlay overlay) {
        synchronized (lock) {
            overlays.remove(overlay);
            overlay.deinit();
        }
    }

    private void tick() {
        synchronized (lock) {
            if (!overlaysToInit.isEmpty()) {
                overlaysToInit.forEach(Overlay::init);
                overlaysToInit.clear();
            }
            overlays.forEach(Overlay::render);
        }
    }
}
