import 'package:plaza_audiovisual_interfaz/pages/home_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/login_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/register_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

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
      home: LoginPage(),
      routes: {
        RegisterPage.routeName:(_) => RegisterPage(),
        LoginPage.routeName:(_) => LoginPage(),
        HomePage.routeName:(_) => HomePage(),
      },
    );
  }
}
