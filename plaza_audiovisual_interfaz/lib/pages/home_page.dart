import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  static const routeName = 'home';

  @override
  _HomePage createState() => _HomePage();

}

class _HomePage extends State<HomePage> {
  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text("HOME PAGE"),
      ),
    );
  }
}