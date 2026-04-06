# KanbanTask - App Android

Projeto Kanban desenvolvido com base no roteiro da Profa. Marta Amorim (Dispositivos Móveis, 2025).

---

## Estrutura do Projeto

```
app/src/main/
├── AndroidManifest.xml
├── java/com/aluno/task/
│   ├── MainActivity.kt
│   ├── data/model/
│   │   ├── Status.kt          (enum: TODO, DOING, DONE)
│   │   └── Task.kt            (data class Parcelable)
│   ├── ui/
│   │   ├── SplashFragment.kt
│   │   ├── HomeFragment.kt
│   │   ├── TodoFragment.kt
│   │   ├── DoingFragment.kt
│   │   ├── DoneFragment.kt
│   │   ├── FormTaskFragment.kt
│   │   ├── adapter/
│   │   │   ├── TaskAdapter.kt      (ListAdapter + DiffUtil)
│   │   │   └── ViewPagerAdapter.kt
│   │   └── auth/
│   │       ├── LoginFragment.kt
│   │       ├── RegisterFragment.kt
│   │       └── RecoverAccountFragment.kt
│   └── util/
│       └── Extensions.kt      (initToolbar, showBottomSheet)
└── res/
    ├── drawable/
    │   ├── bg_btn.xml
    │   ├── bg_edit_text.xml
    │   ├── bg_bottom_sheet.xml
    │   ├── ic_logo.xml
    │   ├── ic_add.xml
    │   ├── ic_back.xml
    │   ├── ic_arrow_back.xml
    │   ├── ic_arrow_forward.xml
    │   └── ic_logout.xml
    ├── layout/
    │   ├── activity_main.xml
    │   ├── fragment_splash.xml
    │   ├── fragment_login.xml
    │   ├── fragment_register.xml
    │   ├── fragment_recover_account.xml
    │   ├── fragment_home.xml
    │   ├── fragment_todo.xml
    │   ├── fragment_doing.xml
    │   ├── fragment_done.xml
    │   ├── fragment_form_task.xml
    │   ├── item_task.xml
    │   └── bottom_sheet.xml
    ├── navigation/
    │   └── main_graph.xml
    └── values/
        ├── colors.xml
        ├── strings.xml
        └── themes.xml
```

---

## Como importar no Android Studio

1. Abra o Android Studio
2. Selecione **File > Open**
3. Navegue até a pasta `KanbanApp` e clique **OK**
4. Aguarde o Gradle sincronizar (pode levar alguns minutos)
5. Clique em **Run** (▶) ou `Shift+F10` para executar

---

## Funcionalidades implementadas

### Partes 1–3: Telas de Autenticação e Navegação
- ✅ SplashFragment com redirecionamento automático (3 segundos)
- ✅ LoginFragment com validação de campos
- ✅ RegisterFragment com Toolbar e validação
- ✅ RecoverAccountFragment com Toolbar e validação
- ✅ Navigation Component com sub-grafo de autenticação
- ✅ popUpTo + popUpToInclusive (desabilita voltar para Splash)
- ✅ TabLayout + ViewPager2 com 3 abas (A Fazer / Fazendo / Concluído)

### Partes 4–5: Formulário e Listagem
- ✅ FormTaskFragment com RadioGroup de status e validação
- ✅ Extension functions: `initToolbar` e `showBottomSheet`
- ✅ BottomSheetDialog customizado (substitui Toast)
- ✅ RecyclerView com TaskAdapter em cada aba
- ✅ Setas de navegação condicionais por status:
  - A Fazer → só seta direita (verde)
  - Fazendo  → seta esquerda (laranja) + seta direita (verde)
  - Concluído → só seta esquerda (laranja)
- ✅ FloatingActionButton para adicionar tarefa

### Parte 6: DiffUtil
- ✅ TaskAdapter com ListAdapter + DiffUtil.ItemCallback
- ✅ submitList() para atualização eficiente
- ✅ companion object com constantes de ação

---

## Personalização para a avaliação

Conforme a tabela de avaliação do PDF, você deve personalizar:

| Item | O que mudar |
|------|-------------|
| **Cores** | Edite `colors.xml` → `color_default`, `color_delete`, `color_details`, etc. |
| **Ícones** | Substitua os arquivos `ic_*.xml` em `drawable/` por outros do Material Icons |
| **Nome do aluno** | Adicione seu nome num `TextView` em 2 telas (ex: fragment_splash.xml e fragment_login.xml) |
| **Dados fictícios** | Edite as listas em `TodoFragment`, `DoingFragment` e `DoneFragment` |

---

## Observações técnicas

- **Firebase**: Os métodos `updateTask` e `deleteTask` estão preparados para integração futura com Firebase Realtime Database. Atualmente operam sobre a lista local em memória.
- **Package**: O package padrão é `com.aluno.task`. Para alterar, use **Refactor > Rename** no Android Studio e atualize o `namespace` no `build.gradle.kts`.
- **minSdk**: 24 (Android 7.0+)
