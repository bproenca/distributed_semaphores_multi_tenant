package br.com.bcp;

/**
 * Only used to input some basic config data for tests.
 */
public enum Tenant {
    A ("tenant_a", "resource_lock_tenant_a", 5, 1),
    B ("tenant_b", "resource_lock_tenant_b", 8, 2),
    C ("tenant_c", "resource_lock_tenant_c", 13, 2),
    D ("tenant_d", "resource_lock_tenant_d", 21, 3);

    private final String queueName;
    private final String resourceLock;
    private final Integer numberOfMessages;
    private final Integer backPressure;

    private Tenant(String queueName, String resourceLock, Integer numberOfMessages, Integer backPressure) {
        this.queueName = queueName;
        this.resourceLock = resourceLock;
        this.numberOfMessages = numberOfMessages;
        this.backPressure = backPressure;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getResourceLock() {
        return resourceLock;
    }

    public Integer getNumberOfMessages() {
        return numberOfMessages;
    }

    public Integer getBackPressure() {
        return backPressure;
    }
    
}
