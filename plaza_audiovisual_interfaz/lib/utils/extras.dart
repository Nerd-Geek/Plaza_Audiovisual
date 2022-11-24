import 'dart:io';

import 'package:image_picker/image_picker.dart';
import 'package:path/path.dart';
class Extras {
  static XFile? file;
  static Future<XFile?> pickImage(bool fromCamara) async{
    final ImagePicker picker = ImagePicker();
    file = await picker.pickImage(
        source: fromCamara?ImageSource.camera:ImageSource.gallery);
    return file;
  }

  static Future getImagen() async {
    final image = await ImagePicker().pickImage(source: ImageSource.gallery);
    if (image == null) return;
    final imageTemporary = File(image.path);
    return imageTemporary;
  }

   String getImage() {
    String? path = file!.path;
    return path;
  }

  static getFileName(String path) {
    return basename(path);
  }
}