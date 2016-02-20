import java.util.ArrayList;

public class EventHandlerReview {
    public static class EventDispatcher {
        private volatile static EventDispatcher instance;
        private ArrayList<EventListener> eventListeners = new ArrayList<EventListener>();

        public static EventDispatcher getInstance() {
            if (instance == null) {
                synchronized (EventDispatcher.class) {
                    if (instance == null) {
                        instance = new EventDispatcher();
                    }
                }
            }
            return instance;
        }

        public void putEvent(Event event) {
            for (EventListener listener : eventListeners) {
                listener.onEvent(event);
            }
        }

        public void addListener(EventListener eventListener) {
            eventListeners.add(eventListener);
        }
    }

    interface EventListener {
        void onEvent(Event event);
    }

    public static class Event {
        private Object payload;

        public Event(Object payload) {
            this.payload = payload;
        }

        public Object getPayload() {
            return payload;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "payload=" + payload +
                    '}';
        }
    }

    private static final Object mutex = new Object();
    private static boolean pushed = false;

    public static class EventPusher extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("pusher before sync...");
                synchronized (mutex) {

                    EventDispatcher.getInstance().putEvent(new Event(">>" + i));
                    pushed = true;
                    mutex.notify();
                    while (pushed) {
                        try {
                            System.out.println("pusher wait...");
                            mutex.wait();
                            System.out.println("pusher awake...");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static class EventListenerRegistrator extends Thread {
        @Override
        public void run() {
            System.out.println("dispatcher before sync...");
            synchronized (mutex) {
                while (!pushed) {
                    try {
                        System.out.println("dispatcher onEvent wait...");
                        mutex.wait();
                        System.out.println("dispatcher onEvent awake...");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                EventDispatcher.getInstance().addListener(new EventListener() {
                    @Override
                    public void onEvent(Event event) {
                        System.out.println("Event: " + event);
                    }
                });
                pushed = false;
                mutex.notify();
            }
        }
    }

    public static void main(String[] args) {
        new EventListenerRegistrator().start();
        new EventPusher().start();
    }
} 