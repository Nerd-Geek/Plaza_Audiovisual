import 'package:get/get.dart';
import 'package:plaza_audiovisual_interfaz/api/my_api.dart';
import 'package:plaza_audiovisual_interfaz/model/user.dart';
import 'package:plaza_audiovisual_interfaz/utils/responsive.dart';
import 'package:plaza_audiovisual_interfaz/widgets/icon_container.dart';
import 'package:plaza_audiovisual_interfaz/widgets/input_text.dart';
import 'package:flutter/material.dart';

class UserProfileForm extends StatelessWidget {
  final String? username;
  final String? pathImage;
  const UserProfileForm({required this.username, required this.pathImage});

  @override
  Widget build(BuildContext context) {

    final Responsive responsive = Responsive.of(context);

    return Column(
        children: [
          Image.network(
              pathImage ??"http://192.168.29.141:6668/rest/files/1667208375733_loading_gif.gif",
              width: responsive.wp(20),
              alignment: Alignment.center,
              ),
          Text(
            username??"null",
            style: TextStyle(
              fontSize: responsive.dp(4)
            ),
          ),
          TextButton(
            onPressed: () {
              Navigator.pushNamed(context, 'modify');
            },
            style: const ButtonStyle(
              foregroundColor: MaterialStatePropertyAll<Color>(Colors.white),
              backgroundColor: MaterialStatePropertyAll<Color>(Colors.pinkAccent),
            ),
            child: Text(
              "Modify User",
              style: TextStyle(
                  fontSize: responsive.dp(2)
              ),
            ),
          )
        ],
      );
  }

}