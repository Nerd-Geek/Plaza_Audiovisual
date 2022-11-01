import 'package:image_picker/image_picker.dart';
import 'package:plaza_audiovisual_interfaz/utils/responsive.dart';
import 'package:plaza_audiovisual_interfaz/widgets/avatar_button.dart';
import 'package:plaza_audiovisual_interfaz/widgets/icon_container.dart';
import 'package:plaza_audiovisual_interfaz/widgets/register_form.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../utils/extras.dart';
import '../widgets/avatar.dart';
import '../widgets/rectangle.dart';



class RegisterPage extends StatefulWidget {
  static const routeName = 'register';
  @override
  _RegisterPage createState() => _RegisterPage();


}
class _RegisterPage extends State<RegisterPage> {


  @override
  Widget build(BuildContext context) {
    final Responsive responsive = Responsive.of(context);
    final double pinkSize = responsive.wp(80);
    final double orangeSize = responsive.wp(57);

    return Scaffold(
      body: GestureDetector(
        onTap: () {
          FocusScope.of(context).unfocus();
        },
        child: SingleChildScrollView(
          child: Container(
            width: double.infinity,
            height: responsive.heigth,
            color: Colors.white,
            child: Stack(
              alignment: Alignment.center,
              children: [
                Positioned(
                  left: -orangeSize * 0.50,
                  top: -orangeSize * 0.2,
                  child: Rectangle(
                    size: orangeSize,
                    colors: const [
                      Color.fromARGB(255, 96, 85, 150),
                      Color.fromARGB(255, 96, 85, 150),
                    ],
                  ),
                ),
                Positioned(
                  right:-pinkSize * 0.10,
                  top: -pinkSize * 0.43,
                  child: Rectangle(
                    size: pinkSize,
                    colors: const [
                      Color.fromARGB(255, 255, 204, 179),
                      Color.fromARGB(255, 255, 204, 179),
                    ],
                  ),
                ),

                Positioned(
                  left: -orangeSize * 0.15,
                  top: -orangeSize * 0.55,
                  child: Rectangle(
                    size: orangeSize,
                    colors: const [
                      Color.fromARGB(255, 242, 147, 147),
                      Color.fromARGB(255, 242, 147, 147),
                    ],
                  ),
                ),

                Positioned(
                  right:-pinkSize * 0.2,
                  top: -pinkSize * 0.8,
                  child: Rectangle(
                    size: pinkSize,
                    colors: const [
                      Color.fromARGB(255, 246, 117, 168),
                      Color.fromARGB(255, 246, 117, 168),
                    ],
                  ),
                ),
                Positioned(
                  top: pinkSize * 0.22,
                  child: Column(
                    children: [

                      Text(
                        "Hello\nSign up to get started",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: responsive.dp(1.8),
                          color: Colors.black87,
                        ),
                      ),

                      SizedBox(height: responsive.dp(1)),
                      const Avatar(
                        path: "https://www.w3schools.com/howto/img_avatar2.png",
                        imageSize: 100,
                      )
                    ],
                  ),
                ),

                RegisterForm(),
                Positioned(
                    left: 15,
                    top: 15,
                    child: SafeArea(
                        child: CupertinoButton(
                          color: Colors.black26,
                          padding: EdgeInsets.all(10),
                          borderRadius: BorderRadius.circular(30),
                          onPressed: () {
                            Navigator.pushNamed(context, 'login');
                          },
                          child: const Icon(Icons.arrow_back),
                        )
                    ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}