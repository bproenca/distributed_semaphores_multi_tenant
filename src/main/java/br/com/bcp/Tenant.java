package br.com.bcp;

public enum Tenant {
    A ("customer_a", "rs_customer_a", 3),
    B ("customer_b", "rs_customer_b", 5),
    C ("customer_c", "rs_customer_c", 8),
    D ("customer_d", "rs_customer_d", 13);

    private final String queue;
    private final String resource;
    private final Integer count;

    private Tenant(String queue, String resource, Integer count) {
        this.queue = queue;
        this.resource = resource;
        this.count = count;
    }

    public String getQueue() {
        return queue;
    }

    public String getResource() {
        return resource;
    }

    public Integer getCount() {
        return count;
    }

    
}
