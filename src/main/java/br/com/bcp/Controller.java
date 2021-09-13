package br.com.bcp;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
    @Autowired
    private Consumer consumer;

    @Autowired
    private Producer producer;

    @Autowired
    private Admin admin;

    private static int count = 1;

    @PostMapping("/consume")
    public String consume() throws InterruptedException, IOException, TimeoutException {
        for (int i = 0; i < 100; i++) {
            for (String queue : Config.myQueues.keySet()) {
                String rsQueue = "rs_" + queue;
                consumer.lockAndConsume(rsQueue);
            }
        }
        return "Iterate 100 times consuming resources";
    }

    @PostMapping("/produce")
    public Map<String, Integer> produce() {
        Config.myQueues.forEach((queue, value) -> {
			for (int i = 0; i < value; i++) {
				producer.produce(queue, "Message number: " + count++);
			}
		});
		return Config.myQueues;
    }

	@PostMapping("/config")
    public String config() {
        for (String queue : Config.myQueues.keySet()) {
            String rsQueue = "rs_" + queue;
            admin.purge(rsQueue);
        	producer.produce(rsQueue, queue);
		}
		return "done";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

}
