import 'dart:io';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:plaza_audiovisual_interfaz/model/media.dart';
import 'package:plaza_audiovisual_interfaz/utils/data_state.dart';
import 'package:provider/provider.dart';

import '../api/my_api.dart';
import '../model/user.dart';
import '../utils/responsive.dart';
import 'card_user.dart';

class ListUser extends StatefulWidget {
  @override
  _ListUser createState() => _ListUser();
}

class _ListUser extends State<ListUser> {

  final List<User> user = <User>[];
  final List<String> medias = <String>[];
  @override
  void initState() {
    super.initState();
      _submit();
    }
  _submit() async {
    MyApi myApi = MyApi.instance;
    await myApi.getMediasUser().then((value) {
      setState(() {
        for(int i = 0; i < value!.length; i++) {
          if(value[i].medias!.isNotEmpty) {
            user.add(value[i]);
          }
        }
      });
    });
  }
  @override
  Widget build(BuildContext context) {
    final Responsive responsive = Responsive.of(context);
    return SizedBox(
      height: responsive.hp(70),
      child: ListView.builder(
        itemCount: user.length,
        itemBuilder: (context, index) {
          return CardUser(
              user[index].name!.value,
              user[index].medias![0].description!.value,
              user[index].medias![0].name!.value,
              user[index].image!.value,
              "heroe $index"
          );
        },
        padding: const EdgeInsets.all(25.0),
        scrollDirection: Axis.vertical,
      ),
    );
  }
}