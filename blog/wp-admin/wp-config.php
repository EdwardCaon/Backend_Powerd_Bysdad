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
define( 'DB_USER', 'mark' );

/** Database password */
define( 'DB_PASSWORD', 'Gatoparlate4' );

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
define( 'AUTH_KEY',         'k$RS$xWRzG~8N)zmmGX9l_=H^d/a2-;*<m=;&?(M61+}LWz!mP1gf)X(S[v@_F3Z' );
define( 'SECURE_AUTH_KEY',  '?M8q[Xl:-]|,?T+*t=5liWH3||;C4XkG+WYu3GZ*^g?b[_<P#JFwVB9B77[@2A?|' );
define( 'LOGGED_IN_KEY',    'NDXi#|xA[Cpt}3*CuwqwBw{nRE.9XS4Q2MFx:b43rhb.geAxbxwa^Q:y,[@^qco^' );
define( 'NONCE_KEY',        ':3oKH:gXS,o0G==E2;0jmj8tEI)5DjfATI*-*|Vo.U=^FngEkEn)*,:0Fhi6^td4' );
define( 'AUTH_SALT',        '#RB38vc}c*CV^H*B|_S(+Jn[2geb*ch(tlp6O2?Dl9jhX|I0ORn]H lWO]{rdLbo' );
define( 'SECURE_AUTH_SALT', ',bvm!D>u6.Uz_]w@@X|9V?0<S|kN~XaC?y5W^k~oH08DnrmO8k(N@cRdXd~<;jYE' );
define( 'LOGGED_IN_SALT',   'Tc$!O; /u}vw!vlRnQ(gz$^gPSzv:B5[mn#sL`xTDH ]pD).TSUOhG/17~rBxHi@' );
define( 'NONCE_SALT',       'A}&M9Gtk9G(.OWbMqBpgxd:dw}=7qtfC8 Ex{+S=z/!{,g__!Ml})38U8h%PbSDG' );

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
