#include <linux/err.h>
#include <linux/ctype.h>
#include <linux/cdev.h>
#include <asm/uaccess.h>
#include <linux/interrupt.h>
#include <linux/input.h>
#include <linux/sysfs.h>
#include "ads7870-core.h"

#define ADS7870_MAJOR       64
#define ADS7870_MINOR        0
#define MAXLEN             512
#define NBR_ADC_CH           8

#define MODULE_DEBUG 1
#define USECDEV 0

static struct class *ads7870_cls;

struct file_operations ads7870_fops = {
		.owner = THIS_MODULE,
		.open = ads7870_cdrv_open,
		.release = ads7870_cdrv_release,
		.write = ads7870_cdrv_write,
		.read = ads7870_cdrv_read, };



static int __init ads7870_cdrv_init(void) {
	int err = 0;

	printk("ads7870 driver initializing\n");

	if (register_chrdev(ADS7870_MAJOR, "adconverter", &ads7870_fops)) {
		printk(KERN_ERR "ADS7870: unable to get major %d\n", ADS7870_MAJOR);
		err = -EIO;
		return err;
	}

	ads7870_cls = class_create(THIS_MODULE, "adconverter");
	if (IS_ERR(ads7870_cls)) {
		err = PTR_ERR(ads7870_cls);
		unregister_chrdev(ADS7870_MAJOR, "adconverter");
		return err;
	}

	device_create(ads7870_cls, NULL, MKDEV(ADS7870_MAJOR, 0), NULL, "adc0");
	device_create(ads7870_cls, NULL, MKDEV(ADS7870_MAJOR, 1), NULL, "adc1");

	printk("ads7870 driver initialized\n");

	return ads7870_init();
}

static void __exit ads7870_cdrv_exit(void) {
	printk("ads7870 driver Exit\n");
	printk(KERN_ERR "ADS7870: exit returned %d\n", ads7870_exit());

	device_destroy(ads7870_cls, MKDEV(ADS7870_MAJOR, 0));
	class_destroy(ads7870_cls);
	unregister_chrdev(ADS7870_MAJOR, "adconverter");
}

int ads7870_cdrv_open(struct inode *inode, struct file *filep) {
	int major, minor;

	printk("ads7870 driver Open\n");

	major = MAJOR(inode->i_rdev);
	minor = MINOR(inode->i_rdev);

	printk("Opening ADS7870 Device [major], [minor]: %i, %i\n", major, minor);

	if (minor > NBR_ADC_CH - 1) {
		printk("Minor no out of range (0-%i): %i\n", NBR_ADC_CH, minor);
		return -ENODEV;
	}

	if (!try_module_get(ads7870_fops.owner)) // Get Module
		return -ENODEV;

	return 0;
}

int ads7870_cdrv_release(struct inode *inode, struct file *filep) {
	int minor, major;

	printk("ads7870 driver Release\n");

	major = MAJOR(inode->i_rdev);
	minor = MINOR(inode->i_rdev);

	printk("Closing ADS7870 Device [major], [minor]: %i, %i\n", major, minor);

	if (minor > NBR_ADC_CH - 1)
		return -ENODEV;

	module_put(ads7870_fops.owner); // Release Module

	return 0;
}

ssize_t ads7870_cdrv_write(struct file *filep, const char __user *ubuf, size_t count, loff_t *f_pos) {
	int minor, len, value;
	char kbuf[MAXLEN];

	printk(KERN_ALERT "ads7870 Write\n");

	minor = MINOR(filep->f_dentry->d_inode->i_rdev);
	if (minor != ADS7870_MINOR) {
		printk(KERN_ALERT "ads7870 Write to wrong Minor No:%i \n", minor);
		return 0;
	}
	printk(KERN_ALERT "Writing to ads7870 [Minor] %i \n", minor);

	len = count < MAXLEN ? count : MAXLEN;
	if (copy_from_user(kbuf, ubuf, len))
		return -EFAULT;

	kbuf[len] = '\0'; // Pad null termination to string

	if (MODULE_DEBUG)
		printk("string from user: %s\n", kbuf);
	sscanf(kbuf, "%i", &value);
	if (MODULE_DEBUG)
		printk("value %i\n", value);

	/*
	 * Write something here
	 */

	return count;
}

ssize_t ads7870_cdrv_read(struct file *filep, char __user *ubuf, size_t count, loff_t *f_pos) {
	int minor;
//	char resultBuf[5];
	u16 result;

	printk(KERN_ALERT "ads7870 Read\n");

	minor = MINOR(filep->f_dentry->d_inode->i_rdev);
	if (minor > NBR_ADC_CH - 1) {
		printk(KERN_ALERT "ads7870 read from wrong Minor No:%i \n", minor);
		return 0;
	}
	if (MODULE_DEBUG)
		printk(KERN_ALERT "Reading from ads7870 [Minor] %i \n", minor);
	printk(KERN_ALERT "ads7870 Read\n");

	/*
	 * Start Conversion
	 */
	result = ads7870_convert(minor & 0xff);

	/*
	 * Convert to string and copy to user space
	 */
//	snprintf(resultBuf, sizeof resultBuf, "%d", result);
//	if (copy_to_user(ubuf, resultBuf, sizeof(resultBuf)))
//		return -EFAULT;
	if (copy_to_user(ubuf, &result, sizeof(result)))
		return -EFAULT;

	return count;
}

module_init(ads7870_cdrv_init);
module_exit(ads7870_cdrv_exit);

MODULE_AUTHOR("Peter Hoegh Mikkelsen <phm@iha.dk>");
MODULE_LICENSE("GPL");

