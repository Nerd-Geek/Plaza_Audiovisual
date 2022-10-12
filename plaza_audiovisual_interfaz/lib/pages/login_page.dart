import 'package:plaza_audiovisual_interfaz/utils/responsive.dart';
import 'package:plaza_audiovisual_interfaz/widgets/icon_container.dart';
import 'package:flutter/material.dart';

import '../widgets/rectangle.dart';
import '../widgets/login_form.dart';



class LoginPage extends StatefulWidget {
  static const routeName = 'login';
  @override
  _LoginPage createState() => _LoginPage();


}
class _LoginPage extends State<LoginPage> {
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
                  top: pinkSize * 0.35,
                  child: Column(
                    children: [
                      IconContainer(
                        size:
                        responsive.wp(15),
                      ),
                      SizedBox(
                        height: responsive.dp(3),
                      ),
                      Text(
                        "Hello Again\nWelcome Back",
                        textAlign: TextAlign.center,
                        style: TextStyle(
                          fontSize: responsive.dp(1.6),
                        ),
                      )
                    ],
                  ),
                ),
                LoginForm()
              ],
            ),
          ),
        ),
      ),
    );
  }
}