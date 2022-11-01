import 'package:plaza_audiovisual_interfaz/pages/home_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/login_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/modify_user_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/register_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:plaza_audiovisual_interfaz/pages/splash_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/nav_bar.dart';
import 'package:plaza_audiovisual_interfaz/widgets/user_profile.dart';
import 'package:plaza_audiovisual_interfaz/widgets/user_profile_form.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {

    SystemChrome.setPreferredOrientations([
      DeviceOrientation.portraitUp,
      DeviceOrientation.portraitDown
    ]);

    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      home: SplashPage(),
      routes: {
        RegisterPage.routeName:(_) => RegisterPage(),
        ModifyUserPage.routeName:(_) => ModifyUserPage(),
        LoginPage.routeName:(_) => LoginPage(),
        HomePage.routeName:(_) => HomePage(),
        NavBar.routeName:(_) => const NavBar(),
      },
    );
  }
}
