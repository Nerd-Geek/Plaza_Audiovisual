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

   String getImage() {
    String? path = file!.path;
    return path;
  }

  static getFileName(String path) {
    return basename(path);
  }
}