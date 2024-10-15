package br.com.bcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @PostMapping("/consume")
    public String consume(
        @RequestParam(value = "threads", defaultValue = "2") Integer threads,
        @RequestParam(value = "iterations", defaultValue = "100") Integer iterations)  
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
            logger.info("Producing {} messages in queue {}", t.getNumberOfMessages(), t.getQueueName());
            for (int i = 0; i < t.getNumberOfMessages(); i++) {
                producer.produce(t.getQueueName(), "Message number: " + cnt++);
            }
            sleep(100);
        }
        return "produce ok";
    }

	@PostMapping("/config")
    public String config() {
        for (Tenant t : Tenant.values()) {
            admin.purge(t.getResourceLock());
            logger.info("Configuring lock queues {} with backpressure {} ", t.getResourceLock(), t.getBackPressure());
            for (int i = 0; i < t.getBackPressure(); i++) {
                producer.produce(t.getResourceLock(), t.getQueueName());
            }
            sleep(100);
        }
		return "config ok";
    }

    @GetMapping("/ping")
    public String ping() {
        logger.info("pong");
        return "pong";
    }

    private void sleep(int time) {
         try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error("Sleep error", e);
        }
    }

}
