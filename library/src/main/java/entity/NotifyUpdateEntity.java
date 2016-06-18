package entity;

/**
 * Created by ybin on 2016/5/18.
 */
public class NotifyUpdateEntity {
    private String notifyTag="";

    public String getNotifyTag() {
        return notifyTag;
    }

    private Object obj;

    public Object getObj() {
        return obj;
    }

    private NotifyUpdateEntity() {
    }

    public NotifyUpdateEntity(String notifyTag, Object obj) {
        this.notifyTag = notifyTag;
        this.obj = obj;
    }
}
