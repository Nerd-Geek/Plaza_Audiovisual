import 'dart:io';

import 'package:cached_network_image/cached_network_image.dart';
import 'package:get/get.dart';
import 'package:image_picker/image_picker.dart';
import 'package:photo_view/photo_view.dart';
import 'package:plaza_audiovisual_interfaz/api/my_api.dart';
import 'package:plaza_audiovisual_interfaz/model/media.dart';
import 'package:plaza_audiovisual_interfaz/model/user.dart';
import 'package:plaza_audiovisual_interfaz/utils/responsive.dart';
import 'package:plaza_audiovisual_interfaz/widgets/input_text.dart';
import 'package:flutter/material.dart';

import '../utils/extras.dart';

class CreateMedia extends StatefulWidget {
  @override
  _CreateMedia createState() => _CreateMedia();
}

class _CreateMedia extends State<CreateMedia> {
  String path = "";
  Media media = Media();
  File? _pickedFile;
  String description = "";
  _pickImage() async {
    final File pickedFile = await Extras.getImagen();
      setState(() {
        _pickedFile = pickedFile;
        //media.name = pickedFile.path.obs;
      });
  }

  _uploadFile() async {
    MyApi myApi = MyApi.instance;
    final bytes = await _pickedFile?.readAsBytes();
    String? path  = _pickedFile?.path;
    await myApi.setMedia(bytes!, path!, media.description!.value, context);
  }

  @override
  Widget build(BuildContext context) {
    final Responsive responsive = Responsive.of(context);

    return SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(height: responsive.dp(5)),
            _pickedFile !=null ? Image.file(
                _pickedFile!,width: responsive.wp(70),
            ): Image.network("http://carlosmoreno.duckdns.org:9090/rest/files/1667988548358_default-image.jpg"),
            SizedBox(height: responsive.dp(2)),
            InputText(
              keyBoardType: TextInputType.text,
              label: "DESCRIPTION",
              fontSize: responsive.dp(2),
              onChanged: (text){
                media.description = text.obs;
              },
              validator: (text) {
                if (text!.isEmpty){
                  return "Invalid description";
                }
                return null;
              },
            ),
            SizedBox(height: responsive.dp(4)),
            SizedBox(
              width: double.infinity,
              child: OutlinedButton (
                onPressed: _pickImage,
                style: ButtonStyle(
                  foregroundColor: const MaterialStatePropertyAll<Color>(Colors.white),
                  backgroundColor: const MaterialStatePropertyAll<Color>(Colors.pinkAccent),
                  padding: MaterialStateProperty.all<EdgeInsets>(
                      const EdgeInsets.symmetric(vertical: 10)),
                ),
                child: Text(
                  "Gallery",
                  style: TextStyle(
                      fontSize: responsive.dp(1.6)
                  ),
                ),
              ),
            ),
            SizedBox(
              width: double.infinity,
              child: OutlinedButton (
                onPressed: _uploadFile,
                style: ButtonStyle(
                  foregroundColor: const MaterialStatePropertyAll<Color>(Colors.white),
                  backgroundColor: const MaterialStatePropertyAll<Color>(Colors.pinkAccent),
                  padding: MaterialStateProperty.all<EdgeInsets>(
                      const EdgeInsets.symmetric(vertical: 10)),
                ),
                child: Text(
                  "Upload",
                  style: TextStyle(
                      fontSize: responsive.dp(1.6)
                  ),
                ),
              ),
            ),
          ],
      )
    );
  }
}