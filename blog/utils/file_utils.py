import os, shutil, filetype


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


def is_image(file_path):
    """判断文件是否是图片"""

    kind = filetype.guess(file_path)
    if not kind:
        return False

    types = ["image/jpeg", "image/png", "image/gif", "image/webp", "image/x-canon-cr2", "image/tiff", "image/bmp",
             "image/vnd.ms-photo", "image/vnd.adobe.photoshop", "image/x-icon"]
    return kind.mime in types


def is_video(file_path):
    """判断文件是否是视频"""

    kind = filetype.guess(file_path)
    if not kind:
        return False

    types = ["video/mp4", "video/x-m4v", "video/x-matroska", "video/webm", "video/quicktime", "video/x-msvideo",
             "video/x-ms-wmv",
             "video/mpeg", "video/x-flv"]
    return kind.mime in types


def is_audio(file_path):
    """判断文件是否是音频"""

    kind = filetype.guess(file_path)
    if not kind:
        return False

    types = ["audio/midi", "audio/mpeg", "audio/m4a", "audio/ogg", "audio/x-flac", "audio/x-wav",
             "audio/amr"]
    return kind.mime in types
