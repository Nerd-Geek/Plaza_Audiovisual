import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../model/user.dart';
import '../utils/responsive.dart';

class UserProfileSearch extends StatelessWidget {
  final User user;
  const UserProfileSearch({required this.user});

  @override
  Widget build(BuildContext context) {

    final Responsive responsive = Responsive.of(context);

    return Column(
      children: [
        SizedBox(height: responsive.dp(2)),
        Image.network(
          user.image!.value ??"http://carlosmoreno.duckdns.org:9090/rest/files/1667208375733_loading_gif.gif",
          width: responsive.wp(20),
          alignment: Alignment.center,
        ),
        Text(
          user.username!.value??"null",
          style: TextStyle(
              fontSize: responsive.dp(4)
          ),
        ),
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
    );
  }

}