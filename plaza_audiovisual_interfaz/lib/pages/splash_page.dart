import 'dart:async';

import 'package:after_layout/after_layout.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:plaza_audiovisual_interfaz/pages/home_page.dart';
import 'package:plaza_audiovisual_interfaz/pages/login_page.dart';
import 'package:plaza_audiovisual_interfaz/utils/auth.dart';

import '../utils/responsive.dart';
import 'nav_bar.dart';
class SplashPage extends StatefulWidget {
  static const routeName = 'splash';
  @override
  _SplashPageState createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> with AfterLayoutMixin{

  @override
  FutureOr<void> afterFirstLayout(BuildContext context) {
    this._check();
  }

  _check() async {
    final String? token = await Auth.instance.accessToken(context);
    print("was logged");
    if(token != null) {
      Navigator.pushReplacementNamed(context, NavBar.routeName);
    } else {
      Navigator.pushReplacementNamed(context, LoginPage.routeName);
    }
  }

  @override
  Widget build(BuildContext context) {
    final Responsive responsive = Responsive.of(context);
   return Scaffold(
      body: Center(
       child: Image.asset(
            "assets/gif/loading_gif.gif",
            height: responsive.hp(90),
            width: responsive.wp(90),
       ),
      )
   );
  }
}