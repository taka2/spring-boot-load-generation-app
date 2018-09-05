package loadgeneration;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GenerateLoadController {
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("form", new GenerateLoadForm());
		return "index";
	}
		
	@PostMapping("/generateload")
	public String generateLoad(Model model, @ModelAttribute("form") GenerateLoadForm form) {
		int iNumThreads = 1;
		if(form.getNumThreads() != null) {
			iNumThreads = form.getNumThreads();
		}
		
		int iLoopCount = 200000;
		if(form.getLoopCount() != null) {
			iLoopCount = form.getLoopCount();
		}
				
		List<Thread> threads = new ArrayList<>();
		
		for(int i=0; i<iNumThreads; i++) {
			Thread t = new Thread(new LoadTask(iLoopCount));
			threads.add(t);
			t.start();
		}
		
		for(Thread t : threads) {
			try {
				t.join();
			} catch(InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
		return "index";
	}
	
	private static class LoadTask implements Runnable {
		private int loopCount;
		
		public LoadTask(int loopCount) {
			this.loopCount = loopCount;
			System.out.println(Thread.currentThread().getName() + "/loopCount: " + this.loopCount);
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() + "/start: " + System.currentTimeMillis());
			long sum = 0;
			for(int i=0; i<this.loopCount; i++) {
				for(int j=0; j<i; j++) {
					sum += j;
				}
			}
			ThreadContext.put("sum", Long.toString(sum));
			System.out.println(Thread.currentThread().getName() + "/end: " + System.currentTimeMillis());
		}
	}
}
