package com.agilsistemas.replication_example;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.agilsistemas.replication_example.view.TelaPrincipal;

@SpringBootApplication
public class ReplicationExampleApplication {

	public static void main(String[] args) {
		setLook();
		ApplicationContext ctx = new SpringApplicationBuilder(ReplicationExampleApplication.class)
				.headless(false).web(WebApplicationType.NONE).run(args);

		TelaPrincipal t = ctx.getBean(TelaPrincipal.class);
		t.setVisible(true);
	}

	/**
	 * Definindo o tema da interface como o tema padrão do SO.
	 */
	private static void setLook() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}
	}

}
