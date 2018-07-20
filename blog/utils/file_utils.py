import os, shutil


def mkdir(path):
    """创建目录"""

    os.mkdir(path)


def del_file(source):
    """删除文件或目录"""

    if os.path.isfile(source):
        os.remove(source)
    else:
        shutil.rmtree(source)


def move_file(source, dest):
    """移动文件"""

    if not exists(source):
        raise Exception("源文件不存在!")

    shutil.move(source, dest)


def get_filename(file_path):
    """获取文件名"""

    filename = os.path.split(file_path)[1]
    return filename


def get_file_suffix(file_path):
    """获取文件后缀"""

    return os.path.splitext(file_path)[1]


def exists(file_path):
    """检查文件是否存在"""

    return os.path.exists(file_path)
