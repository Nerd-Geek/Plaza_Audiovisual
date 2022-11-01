import 'dart:math';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:plaza_audiovisual_interfaz/utils/data_state.dart';
import 'package:plaza_audiovisual_interfaz/utils/extras.dart';
import 'package:plaza_audiovisual_interfaz/widgets/avatar_button.dart';
import 'package:plaza_audiovisual_interfaz/widgets/list_user.dart';
import 'package:provider/provider.dart';

import '../utils/responsive.dart';
import '../widgets/logout_button.dart';

class HomePage extends StatefulWidget {
  static const routeName = 'home';

  @override
  _HomePage createState() => _HomePage();

}

class _HomePage extends State<HomePage> {



  @override
  Widget build(BuildContext context) {
    final Responsive responsive = Responsive.of(context);
    return Scaffold(
      body: Column(
        children: [
          const LogoutButton(),
          ChangeNotifierProvider(
            create: (BuildContext context) => DataState(),
            child: ListUser(),
          )
        ],
      ),

    );
  }
}