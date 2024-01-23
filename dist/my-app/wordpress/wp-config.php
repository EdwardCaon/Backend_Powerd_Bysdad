<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the installation.
 * You don't have to use the web site, you can copy this file to "wp-config.php"
 * and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * Database settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://wordpress.org/documentation/article/editing-wp-config-php/
 *
 * @package WordPress
 */

// ** Database settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define( 'DB_NAME', 'hoopsdata' );

/** Database username */
define( 'DB_USER', 'backend' );

/** Database password */
define( 'DB_PASSWORD', '111' );

/** Database hostname */
define( 'DB_HOST', 'localhost' );

/** Database charset to use in creating database tables. */
define( 'DB_CHARSET', 'utf8mb4' );

/** The database collate type. Don't change this if in doubt. */
define( 'DB_COLLATE', '' );

/**#@+
 * Authentication unique keys and salts.
 *
 * Change these to different unique phrases! You can generate these using
 * the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}.
 *
 * You can change these at any point in time to invalidate all existing cookies.
 * This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define( 'AUTH_KEY',         '+{0g+SYiPmCWLh1BnsQPb6$bKp{cjUc%P-BIHyAKLYRr$|;uPwn-ePv*Hy]t=e|>' );
define( 'SECURE_AUTH_KEY',  'vdJ-OTQ^t1w>dNIU:a%QJ>N~DwMD_YEl[N)Y*3%pzye:EAq[$}ds=+wbo,K><A(4' );
define( 'LOGGED_IN_KEY',    '&g(K#7`&?SFjwF8O-Q]c:875_5)@SL(zWY1Yp3n_)JP~1 |eYh<cmG+BPr2s>` 6' );
define( 'NONCE_KEY',        '&`z$W#=F~<w=s6)yV5E7;vq]_{{b7j|s?Hd?Ey=-.-P$MCIr:{T0[Q2X4}gQf~oH' );
define( 'AUTH_SALT',        '4C{nPso+>{@) 6tB(:pzU1m2>6J 0<X{(Y4b?0)Vffev:--CgZIv04+8R{KE)3)H' );
define( 'SECURE_AUTH_SALT', '1H{ASm3rsA@DH04BR$yI?!#Z)a/IIo69+&9fVOk~Nw$-yV^Xb,;n18y;A|~X]UQy' );
define( 'LOGGED_IN_SALT',   'TU2.,8iU4JOY]%]Sj-hOBjNTjYkpuIEX+EnftELZ]-djCGVqg:47!Lk(Jh-yM`*m' );
define( 'NONCE_SALT',       '6sNeBs?6RIz~cxQ4[2Sv!%;nXG^@Mk|zBExCZ%w2YK[2d MK>aOcrhN)7ipm_3V,' );

/**#@-*/

/**
 * WordPress database table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix = 'wp_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the documentation.
 *
 * @link https://wordpress.org/documentation/article/debugging-in-wordpress/
 */
define( 'WP_DEBUG', false );

/* Add any custom values between this line and the "stop editing" line. */



/* That's all, stop editing! Happy publishing. */

/** Absolute path to the WordPress directory. */
if ( ! defined( 'ABSPATH' ) ) {
	define( 'ABSPATH', __DIR__ . '/' );
}

/** Sets up WordPress vars and included files. */
require_once ABSPATH . 'wp-settings.php';
