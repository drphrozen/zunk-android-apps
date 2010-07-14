obj-m += ads7870mod.o
ads7870mod-objs := ads7870.o ads7870-core.o ads7870-spi.o
KERNEL_DIR ?= $(HOME)/0xdroid/kernel
ARCH=arm
CROSS_COMPILE=$(HOME)/0xdroid/arm-android-eabi-4.4.1/bin/arm-android-eabi-

all: modules

modules:
	$(MAKE) -C $(KERNEL_DIR) M=`pwd` ARCH=$(ARCH) CROSS_COMPILE=$(CROSS_COMPILE) modules

modules_install:
	$(MAKE) -C $(KERNEL_DIR) M=`pwd` ARCH=$(ARCH) CROSS_COMPILE=$(CROSS_COMPILE) modules_install

clean:
	rm -rf *.o *~ core .depend .*.cmd *.ko *.mod.c .tmp_versions modules.order Module.symvers Module.markers
