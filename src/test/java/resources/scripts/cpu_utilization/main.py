from java.lang.management import ManagementFactory

def get_cpu_load():
    operating_system_bean = ManagementFactory.getOperatingSystemMXBean()
    load = operating_system_bean.getSystemCpuLoad() * 100
    return load

print(get_cpu_load())
