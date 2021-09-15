package br.com.bcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
    @Autowired
    private Consumer consumer;

    @Autowired
    private Producer producer;

    @Autowired
    private Admin admin;

    @PostMapping("/consume")
    public String consume(
        @RequestParam(value = "threads", defaultValue = "2") Integer threads,
        @RequestParam(value = "iterations", defaultValue = "30") Integer iterations)  
    {
        for (int i = 0; i < threads; i++) {
            consumer.startConsume(iterations);
        }
        return "started consuming";
    }

    @PostMapping("/produce")
    public String produce() {
        int cnt = 1;
        for (Tenant t : Tenant.values()) {
            for (int i = 0; i < t.getCount(); i++) {
                producer.produce(t.getQueue(), "Message number: " + cnt++);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return "produce ok";
    }

	@PostMapping("/config")
    public String config() {

        for (Tenant t : Tenant.values()) {
            admin.purge(t.getResource());
        	producer.produce(t.getResource(), t.getQueue());
        }
		return "config ok";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
