import 'dart:io';

import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:plaza_audiovisual_interfaz/utils/data_state.dart';
import 'package:plaza_audiovisual_interfaz/widgets/user_profile_form.dart';
import 'package:provider/provider.dart';

import '../api/my_api.dart';
import '../model/user.dart';
import '../utils/responsive.dart';
import 'card_user.dart';

class UserProfile extends StatefulWidget {
  static const routeName = 'userProfile';
  const UserProfile({super.key});

  @override
  _UserProfile createState() => _UserProfile();
}

class _UserProfile extends State<UserProfile> {

  final User user = User();
  @override
  void initState() {
    super.initState();
    _submit();
  }
  _submit() async {
    MyApi myApi = MyApi.instance;
    String username = await myApi.getUsername()??"";

    await myApi.getUserInfo(username: username).then((value) {
        setState(() {
          user.username = value?.username;
          user.image = value?.image;
          user.medias = value?.medias;
        });
    });
  }
  @override
  Widget build(BuildContext context) {

    final Responsive responsive = Responsive.of(context);
    return SizedBox(
      height: responsive.hp(75),
      child: Column(
       children: [
       UserProfileForm(
          username: user.username?.value,
          pathImage: user.image?.value),
        SizedBox(
          height: responsive.hp(50),
          child: ListView.builder(
            itemCount: user.medias?.length ?? 0,
            itemBuilder: (context, index) {
              return Column(
                children: [
                  Image.network(
                    user.medias![index].name!.value
                  ),
                  SizedBox(height: responsive.dp(4)),
                ],
              );
            },
            padding: const EdgeInsets.all(25.0),
            scrollDirection: Axis.vertical,
            shrinkWrap: true,
          ),
        )
       ],
      )
    );
  }
}