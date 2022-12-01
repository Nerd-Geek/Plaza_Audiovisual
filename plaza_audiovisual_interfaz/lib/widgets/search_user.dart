import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:plaza_audiovisual_interfaz/widgets/search_user_delegate.dart';

import '../api/my_api.dart';
import '../model/user.dart';
import '../utils/responsive.dart';

class SearchUser extends StatefulWidget {
  @override
  _SearchUser createState() => _SearchUser();
}

class _SearchUser extends State<SearchUser> {

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
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        SizedBox(height: responsive.dp(5)),
        IconButton(
          icon: const Icon(Icons.search),
          onPressed: () {
            showSearch(
                context: context, delegate: SearchUserDelegate(user),
            );

          },
        ),
        SizedBox(
          height: responsive.hp(70),
          child: ListView.builder(
            itemCount: user.length,
            itemBuilder: (context, index) {
              return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Row(
                    children: [
                      ClipOval(
                        child: Image.network(
                            user[index].image!.value,
                            width: responsive.wp(11),
                            height: responsive.hp(7),
                            fit: BoxFit.cover
                        ),
                      ),
                      SizedBox(width: responsive.dp(2)),
                      Text(
                        user[index].username!.value,
                        style: TextStyle(fontSize: responsive.dp(2)),
                      ),
                    ],
                  ),

                  SizedBox(height: responsive.dp(2)),
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