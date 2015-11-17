package br.jus.stf.plataforma.shared.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.plataforma.shared.settings.Profiles;

/**
 * @author Lucas.Rodrigues
 *
 */
@Profile(Profiles.DESENVOLVIMENTO)
@RestController
@RequestMapping("/api/management")
public class ManagementResource {
	
	@Autowired
	private ConfigurableApplicationContext context;

	@RequestMapping("/turnoff")
	public String turnoff() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(500L);
				}
				catch (InterruptedException ex) {
				}
				context.close();
			}
		}).start();
		return "Turning off application!";
	}
	
}
