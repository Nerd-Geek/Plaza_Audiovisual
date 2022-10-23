import 'package:flutter/cupertino.dart';
import 'package:cupertino_icons/cupertino_icons.dart';
import 'package:flutter/material.dart';
import 'package:plaza_audiovisual_interfaz/utils/responsive.dart';

class Dialogs {
  static info(BuildContext context, {String? title, String? content}) {
    showDialog(
        context: context,
        builder: (_) => CupertinoAlertDialog(
          title: title != null? Text(title!):null,
          content: title != null? Text(content!): null,
          actions: [
            CupertinoDialogAction(
              child: Text("OK"),
              onPressed: () {
                Navigator.pop(context);
              },
            )
          ],
        ),
    );
  }
}

class ProgressDialog {
  final BuildContext context;

  ProgressDialog (this.context);

  void show() {
    final Responsive responsive = Responsive.of(context);
    showCupertinoModalPopup(
        context: context,
        builder: (_) => Container(
          width: double.infinity,
          height: double.infinity,
          child: Center(
            //child: CircularProgressIndicator(),
            child: Image.asset(
              "assets/gif/loading_gif.gif",
              height: responsive.hp(90),
              width: responsive.wp(90),
            ),
          ),
        ),
    );
  }

  void dissmiss() {
    Navigator.pop(context);
  }
}