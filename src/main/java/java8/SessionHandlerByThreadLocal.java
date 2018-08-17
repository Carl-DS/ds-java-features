package java8;

/**
 * @author duosheng
 * @since 2018/7/9
 */
public class SessionHandlerByThreadLocal {
    /**
     * 使用ThreadLocal 改造后的代码， 不再需要在各个方法间传递Session对象，并且也非常轻松的保证了每个线程
     * 拥有自己独立的实例，同时满足变量在线程间的隔离与方法间的共享，ThreadLocal再合适不过
     */
    private static ThreadLocal<Session> session = new ThreadLocal<>();

    public static class Session {
        private String id;
        private String user;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public void createSession() {
        session.set(new Session());
    }

    public String getUser() {
        return session.get().getUser();
    }

    public String getStatus() {
        return session.get().getStatus();
    }
    public void setStatus(String status) {
        session.get().setStatus(status);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            SessionHandlerByThreadLocal sessionHandler = new SessionHandlerByThreadLocal();
            sessionHandler.createSession();
            sessionHandler.getStatus();
            sessionHandler.getUser();
            sessionHandler.setStatus("close");
            System.out.println(sessionHandler.getStatus());
        }).start();
    }
}
