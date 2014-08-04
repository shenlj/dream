package com.wholetech.commons.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	private static String CONFIG_FILE_LOCATION = "/conf/hibernate.cfg.xml";
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static Configuration configuration = new Configuration();
	private static org.hibernate.SessionFactory sessionFactory;
	private static String configFile = HibernateSessionFactory.CONFIG_FILE_LOCATION;

	static {
		try {
			HibernateSessionFactory.configuration.configure(HibernateSessionFactory.configFile);
			HibernateSessionFactory.sessionFactory = HibernateSessionFactory.configuration.buildSessionFactory();
		} catch (final Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	private HibernateSessionFactory() {

	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the <code>SessionFactory</code> if needed.
	 *
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {

		Session session = HibernateSessionFactory.threadLocal.get();

		if (session == null || !session.isOpen()) {
			if (HibernateSessionFactory.sessionFactory == null) {
				HibernateSessionFactory.rebuildSessionFactory();
			}
			session = (HibernateSessionFactory.sessionFactory != null)
					? HibernateSessionFactory.sessionFactory.openSession()
							: null;
					HibernateSessionFactory.threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 */
	public static void rebuildSessionFactory() {

		try {
			HibernateSessionFactory.configuration.configure(HibernateSessionFactory.configFile);
			HibernateSessionFactory.sessionFactory = HibernateSessionFactory.configuration.buildSessionFactory();
		} catch (final Exception e) {
			System.err.println("%%%% Error Creating SessionFactory %%%%");
			e.printStackTrace();
		}
	}

	/**
	 * Close the single hibernate session instance.
	 *
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {

		final Session session = HibernateSessionFactory.threadLocal.get();
		HibernateSessionFactory.threadLocal.set(null);

		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {

		return HibernateSessionFactory.sessionFactory;
	}

	/**
	 * return session factory
	 * session factory will be rebuilded in the next call
	 */
	public static void setConfigFile(final String configFile) {

		HibernateSessionFactory.configFile = configFile;
		HibernateSessionFactory.sessionFactory = null;
	}

	/**
	 * return hibernate configuration
	 */
	public static Configuration getConfiguration() {

		return HibernateSessionFactory.configuration;
	}

}
