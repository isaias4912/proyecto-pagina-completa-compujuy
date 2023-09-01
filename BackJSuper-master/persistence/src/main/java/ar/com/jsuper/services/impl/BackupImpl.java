/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.services.impl;

import ar.com.jsuper.dto.ConfiguracionDTO;
import ar.com.jsuper.services.Backup;
import ar.com.jsuper.utils.OSValidator;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import javax.transaction.Transactional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 *
 * @author rafa
 */
@Component
public class BackupImpl implements Backup {

	@Autowired
	@Qualifier("threadPoolTaskScheduler")
	TaskScheduler taskScheduler;

	ScheduledFuture<?> scheduledFuture;

	@Autowired
	UtilServiceImpl utilServiceImpl;

	@Value("${backup.idapp}")
	protected Integer backupIdApp;
	@Value("${backup.enabled}")
	protected Boolean backupEnabled;

	@Value("${backup.pathbackup}")
	protected String pathBackup;

	@Value("${backup.pathMSDWindows}")
	protected String pathMSDWindows;

	@Value("${backup.dataBaseName}")
	protected String dataBaseName;

	@Value("${spring.datasource.username}")
	protected String username;

	@Value("${spring.datasource.password}")
	protected String pass;
	private Logger logger = Logger.getLogger(UtilServiceImpl.class);

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		this.start();
	}

//	@Scheduled(cron = "0 0 12 ? * *")
//	public void scheduleFixedDelayTask() {
//		System.out.println(
//				"Fixed delay task -------------------------------------------------------:s " + System.currentTimeMillis() / 1000);
//	}
	@Transactional
	@Override
	public void start() {
		if (backupEnabled) {
			ConfiguracionDTO configuracion = utilServiceImpl.getConfiguracionFromId(backupIdApp);
			if (!Objects.isNull(configuracion.getBackupHab())) {
				if (configuracion.getBackupHab()) {
					scheduledFuture = taskScheduler.schedule(printHour(), new CronTrigger(configuracion.getBackupCron()));
				}
			}
		}
	}

	public void start(String cron) {
		if (backupEnabled) {
//			if (scheduledFuture.isDone()) {
//				System.out.println("STOPPPPPPPPPPPPPPPPPPPPP");
			this.stop();
//			}
			scheduledFuture = taskScheduler.schedule(printHour(), new CronTrigger(cron));
		}
	}

	public void stop() {
//		if (scheduledFuture.isDone()) {
		scheduledFuture.cancel(false);
//		}
	}

	private Runnable printHour() {
		return () -> this.generateBackup();
	}

	@Override
	public String generateBackup() {
		try {
			SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy_HHmmss");
			String nameFile = "backup_db_" + format.format(new Date()) + ".sql";
			logger.info("Realizando Backup:" + nameFile);
			String dump = "";
			Process p;
			if (OSValidator.isWindows()) {
				p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", this.pathMSDWindows + "mysqldump -u " + this.username + " -p" + this.pass + " " + this.dataBaseName + " > " + this.pathBackup + "/" + nameFile});
			} else {
				dump = "mysqldump -u " + this.username + " -p" + this.pass + " " + this.dataBaseName + " > " + this.pathBackup + "/" + nameFile;
				String[] cmdarray = {"/bin/sh", "-c", dump};
				p = Runtime.getRuntime().exec(cmdarray);
			}
			p.waitFor();
			if (p.exitValue() == 0) {
				logger.info("Backup realizado correctamente:" + nameFile);
				return nameFile;
			} else {
				InputStream errorStream = p.getErrorStream();
				byte[] buffer = new byte[errorStream.available()];
				errorStream.read(buffer);
				String str = new String(buffer);
				logger.error("Hubo un error en la ejecucion del comando:1-" + str);
				throw new DataIntegrityViolationException("Hubo un error en la ecejucion del comando:1");
			}
		} catch (IOException ex) {
			logger.error("Hubo un error en la ecejucion del comando:2", ex);
			throw new DataIntegrityViolationException("Hubo un error en la ecejucion del comando:2");
		} catch (InterruptedException ex) {
			logger.error("Hubo un error en la ecejucion del comando:3", ex);
			throw new DataIntegrityViolationException("Hubo un error en la ecejucion del comando:3");
		}
	}
}
