package com.xinran.event;

/**
 * @author 高海军 帝奇 Apr 17, 2015 10:04:20 PM
 */
public abstract class AbstractEvent implements Event {

    protected String type;

    public AbstractEvent(String type) {
        super();
        this.type = type;
    }

    
    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AbstractEvent other = (AbstractEvent) obj;
        if (type == null) {
            if (other.type != null) return false;
        } else if (!type.equals(other.type)) return false;
        return true;
    }
    

}
